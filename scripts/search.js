/**
 * scripts/search.js - librecipe
 * Runs a search against allrecipes.com
 */
'use strict';

const query = process.argv.slice(2).join(' ')
    , LogisticRegressionClassifier = require('natural').LogisticRegressionClassifier
    , metric = /(kg|kilogram|g|gram|pound|cup|tablespoon|teaspoon|can)(s?)/gi
    , fs = require('fs')
    , path = require('path')

//console.log(' * Searching for "%s" ...', query)
require('./allrecipes')(query).then(recipes => {
    let db = ''

    recipes.map(recipe => {
        const ingredientClassifier = new LogisticRegressionClassifier()

        // parse ingredients into pairs of measurements and ingredients
        recipe.ingredients = recipe.ingredients.map((text, index) => {
            let i = 0
              , amount = ''
              , units = ''

            // separate text from amount
            for (i = 0; i < text.length; i += 1) {
                if (text[i].match(/[a-z]/i)) {
                    text = text.substr(i)
                    text = text.split(/\s+/g)

                    let tmp = (text[0].match(metric) || []).filter(e => e)
                    if (tmp.length === 1) {
                        units = text[0]
                        text = text.slice(1)
                    }

                    break
                } else amount += text[i]
            }

            // reduce amount into a single value
            amount = amount.split(/\s+/g).reduce((prev, num) => {
                if (!num) return prev
                num = num.split('/')
                return prev + (num.length === 2 ? parseInt(num[0], 10) / parseInt(num[1], 10) : parseFloat(num.join('')))
            }, 0)

            text = text.join(' ')

            /*if (!text) {
                console.log([[amount, units], text])
            }*/

            ingredientClassifier.addDocument(text, String(index))

            return [[amount, units || 'units'], text]
        })

        // train classifier
        ingredientClassifier.train()

        // fix up quantities
        recipe.ingredients = recipe.ingredients.map(i => {
            if (i[0][0] === 0) i[0][0] = Math.round(Math.random() * 200) / 10
            return i
        })

        // run classifier over steps to cluster by ingredients
        let visited = {}
        recipe.steps = recipe.steps.map(text => {
            let step = {
                ingredients: [],
                text: text
            }

            ingredientClassifier.getClassifications(text)
                .filter(cf => !visited[cf.label] && cf.value > 0.5)
                .forEach(cf => {
                    //console.log(recipe.ingredients[+cf.label])
                    step.ingredients.push(JSON.parse(JSON.stringify(recipe.ingredients[+cf.label])))
                    visited[cf.label] = true
                })

            recipe.ingredients.forEach((i, index) => {
                if (!visited[index] && text.indexOf(i[1]) !== -1) {
                    //console.log(i[1])
                    step.ingredients.push(JSON.parse(JSON.stringify(recipe.ingredients[index])))
                    visited[index] = true
                }
            })

            return step
        })

    recipe.ingredients.filter((_, i) => !visited[i]).forEach(i => {
        recipe.steps[ Math.floor(Math.random() * recipe.steps.length) ].ingredients.push(i)
    })

    delete recipe.ingredients

    let ping = {}
    recipe.psteps = recipe.steps.filter(i => {
        return i.text.match(/prepare|preheat/gi)
    }).map((s, _, a) => {
        s.time = recipe.prept / a.length

        s.ingredients.map(i => {
            ping[i[1]] = {
                name: i[1],
                quantity: i[0][0],
                units: i[0][1]
            }
        })

        s.ingredients = ping

        return s
    })

    ping = {}
    recipe.steps = recipe.steps.filter(i => {
        return recipe.psteps.indexOf(i) === -1
    }).map((s, _, a) => {
        s.time = recipe.cookt / a.length

        s.ingredients.map(i => {
            ping[i[1]] = {
                name: i[1],
                quantity: i[0][0],
                units: i[0][1]
            }
        })

        s.ingredients = ping

        return s
    })

    delete recipe.prept
    delete recipe.cookt

	db += JSON.stringify(recipe) + '\n'
    })

    fs.writeFileSync(path.resolve(__dirname, 'recipes.jsonish'), db)
}, console.log)