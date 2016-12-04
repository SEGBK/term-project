/**
 * scripts/search.js - librecipe
 * Runs a search against allrecipes.com
 */
'use strict';

const query = process.argv.slice(2).join(' ')
    , LogisticRegressionClassifier = require('natural').LogisticRegressionClassifier
    , metric = /(kg|kilogram|g|gram|pound|cup|tablespoon|teaspoon|can)(s?)/gi

console.log(' * Searching for "%s" ...', query)
require('./allrecipes')(query).then(recipes => {
    console.log(JSON.stringify(recipes.map(recipe => {
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

            if (!text) {
                console.log([[amount, units], text])
            }

            console.log('doc: %s', text)
            ingredientClassifier.addDocument(text, String(index))

            return [[amount, units], text]
        })

        // train classifier
        ingredientClassifier.train()

        // run classifier over steps to cluster by ingredients
        let map = {}
        recipe.steps = recipe.steps.map(text => {
            let step = {
                ingredients: [],
                text: text
            }
	    console.log(text)
            ingredientClassifier.getClassifications(text).forEach(classification => {
        		console.log('%s => %s', recipe.ingredients[+classification.label][1], classification.value)
                if (classification.value > 0.4) {
                    let ingredient = JSON.parse(JSON.stringify(recipe.ingredients[+classification.label]))
                    if (map[classification.label]) ingredient[0][0] = 0
                    map[classification.label] = true
                    step.ingredients.push(ingredient)
                }
            })

            return step
        })
        
    delete recipe.ingredients

	return recipe
    }), null, 2))
}, console.log)