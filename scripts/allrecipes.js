/**
 * scripts/allrecipes.js - librecipe
 * Crawler that fetches a collection of recipes from
 * allrecipes.com.
 */

'use strict';

const url = require('url')
    , format = require('util').format
    , cheerio = require('cheerio')
    , request = require('request')

module.exports = query => new Promise((resolve, reject) => {
    const path = format('http://allrecipes.com/search/results/?wt=%s&sort=re', encodeURIComponent(query))
    request(path, (err, res, body) => {
        if (err || !res || res.statusCode > 399) {
            reject(err || ('Something went wrong.' + (res ? ' (code: ' + res.statusCode + ')' : '')))
        } else {
            const $_ = cheerio.load(body)
                , dict = []
                , rows = []

            // parse single column results for nutritional facts
            $_('article.grid-col--fixed-tiles a[href*="/recipe/"]').each((i, row) => {
                let href = $_(row).attr('href')
                if (rows.indexOf(href) === -1) {
                    rows.push(href)
                    /*LIMIT*/if(i>1)return;/*LIMIT*/
                    dict.push(new Promise((resolve, reject) => {
                        request(url.resolve(path, href), (err, res, page) => {
                            if (err || !res || res.statusCode > 399) {
                                reject(err || ('Something went wrong.' + (res ? ' (code: ' + res.statusCode + ')' : '')))
                            } else {
                                const $ = cheerio.load(page)
                                    , clean = text => text && text !== 'ADVERTISEMENT'
                                    , convert = {
                                        'm': m => m
                                    }
                                    , time = text => {
                                        let num = parseFloat(text.find('span').text())
                                          , unit = text.text().replace(new RegExp('' + num, 'g'), '').trim()
                                        return convert[unit](num) || [num, unit]
                                      }
                                    , obj = {
                                        name: $('[itemprop="name"]').text(),
                                        servings: parseFloat($('[ng-model="servings"]').val()),
                                        prept: time($('[itemprop="prepTime"]')),
                                        cookt: time($('[itemprop="cookTime"]')),
                                        ingredients: [],
                                        steps: []
                                    }

                                // pull all ingredients
                                $('[class*="list-ingredients"] li span').each((_, span) => {
                                  let text = $(span).text()
                                  if (clean(text)) obj.ingredients.push(text)
                                })

                                // add all steps
                                $('[itemprop="recipeInstructions"] .step span').each((_, span) => clean($(span).text()) && obj.steps.push($(span).text()))

                                // return obj
                                console.log(JSON.stringify(obj, null, 2))
                                resolve(obj)
                            }
                        })
                    }))
                }
            })

            Promise.all(dict).then(resolve, reject)
        }
    })
})
