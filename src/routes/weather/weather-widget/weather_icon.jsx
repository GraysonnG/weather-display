/** @jsxRuntime classic */
/** @jsx h */
import { h, Fragment } from '../../../lib/jsx.js';
import fs from 'fs';
import path from 'path';
import { loadImage } from '../../../helpers/imagehelper.js';

const MAP = {
  "200": "scattered-thunderstorms-day",
  "201": "thunderstorms",
  "202": "severe-thunderstorm",
  "210": "isolated-thunderstorms-day",
  "211": "thunderstorms",
  "212": "severe-thunderstorm",
  "221": "severe-thunderstorm",
  "230": "scattered-thunderstorms-day",
  "231": "scattered-thunderstorms-day",
  "232": "thunderstorms",
  "300": "rainy-1-day",
  "301": "rainy-1-day",
  "302": "rainy-2-day",
  "310": "rainy-1-day",
  "311": "rainy-1-day",
  "312": "rainy-2-day",
  "313": "rainy-2-day",
  "314": "rainy-2-day",
  "321": "rainy-1-day",
  "500": "rainy-1-day",
  "501": "rainy-2-day",
  "502": "rainy-3-day",
  "503": "rainy-3-day",
  "504": "rainy-3-day",
  "511": "rain-and-sleet-mix",
  "520": "rainy-1-day",
  "521": "rainy-2-day",
  "522": "rainy-3-day",
  "531": "rainy-2-day",
  "600": "snowy-1-day",
  "601": "snowy-2-day",
  "602": "snowy-3-day",
  "611": "snow-and-sleet-mix",
  "612": "rain-and-sleet-mix",
  "613": "snow-and-sleet-mix",
  "615": "rain-and-snow-mix",
  "616": "rain-and-snow-mix",
  "620": "snowy-1-day",
  "621": "snowy-2-day",
  "622": "snowy-3-day",
  "701": "fog-day",
  "711": "haze-day",
  "721": "haze-day",
  "731": "dust",
  "741": "fog-day",
  "751": "dust",
  "761": "dust",
  "762": "haze",
  "771": "wind",
  "781": "tornado",
  "800": "clear-day",
  "801": "cloudy-1-day",
  "802": "cloudy-2-day",
  "803": "cloudy-3-day",
  "804": "cloudy"
}

export const getIconUrlFromCode = (code, night = false) => {
    let iconName = (MAP[`${code}`] ?? "clear-day")
    if (night) iconName = iconName.replaceAll("-day", "-night")
    const data = loadImage(`weather/${iconName}.svg`)
    return data
}