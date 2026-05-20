import { OPEN_WEATHER_MAP_KEY } from "$env/static/private"
import { exampleResponse } from "../temp/res"

const apiURL = "https://api.openweathermap.org"

export const getOpenWeatherMapData = async (
    lat = 39.0321,
    lon = -77.4161
) => {
    // const url = `${apiURL}/data/3.0/onecall?lat=${lat}&lon=${lon}&exclude=minutely&appid=${OPEN_WEATHER_MAP_KEY}&units=imperial`
    // const data = await fetch(url)
    // const rawData = await data.json()
    const rawData = exampleResponse
    return {
        current: {
            ...rawData.current,
            today: rawData.daily[0],
        },
        hourly: [...rawData.hourly.slice(0, 12).map(h => ({
            ...h,
            night: getNight(h, rawData.daily)
        }))],
        daily: [...rawData.daily.slice(1, 11)],
    }
}

const getNight = (hourlyItem, dailyItems) => {
    const hourItemDate = new Date(hourlyItem.dt * 1000)
    let night = false
    dailyItems
        .map(i => ({
            day: (new Date(i.dt * 1000)).getDate(),
            sunrise: i.sunrise,
            sunset: i.sunset
        }))
        .forEach(dailyItemDates => {
            if (dailyItemDates.day === hourItemDate.getDate()) {
                // if its before sunrise its night
                if (dailyItemDates.sunrise >= hourlyItem.dt) night = true
                // if its after sunset its night
                if (dailyItemDates.sunset <= hourlyItem.dt) night = true
            }
        })


    return night
}
