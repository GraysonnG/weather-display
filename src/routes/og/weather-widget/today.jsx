/** @jsxRuntime classic */
/** @jsx h */
import { h, Fragment } from '../../../lib/jsx.js';
import { TempChart } from '../../../helpers/chart.jsx';
import { getIconUrlFromCode } from '../../../helpers/weatherIcon.jsx';

const sectionStyles = {
    display: 'flex',
    flexDirection: 'row',
    gap: 15,
    paddingBottom: 15,
}

const bigTempSectionStyles = {
    display: 'flex',
    position: 'relative',
    justifyContent: 'center',
    alignItems: 'center',
    margin: 0,
    width: '15%',
    height: 200,
}

const bigTempItemStyles = {
    position: 'relative',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    flexDirection: 'column',
    width: '100%',
    height: '100%',
}

const bigTempTextStyles = {
    display: 'flex',
    fontSize: 120,
    fontWeight: 900,
    marginTop: -40,
    marginBottom: -30,
}
const bigTempSubtitleStyles = {
    fontSize: 18,
    fontWeight: 400
}

const nextTwelveHoursStyles = {
    display: 'flex',
    flex: 1,
    flexDirection: 'row',
    gap: 0,
}

const hourStyles = {
    display: 'flex',
    position: 'relative',
    overflow: 'hidden',
    flexDirection: 'column',
    flex: 1,
    alignItems: 'center',
    borderLeft: '2px solid #0002',
    height: 200,
    padding: 5,
}

const popStyles = (pop) => ({
    display: 'flex',
    position: 'absolute',
    bottom: 0,
    left: 0,
    width: '100%',
    height: `${pop * 100}%`,
    background: 'linear-gradient(0deg, #fff0 10%, #99f6)'
})

export const TodaySection = ({currentData, hourlyData}) => (<section style={sectionStyles}>
    <BigTempSection data={currentData} />
    <div style={nextTwelveHoursStyles}>
        <TempChart temps={
            hourlyData.map(d => d.temp)
        } />
        {hourlyData.map((d, i) => (<HourItem data={d} />))}
    </div>
</section>)

const HourItem = ({data}) => {
    const currentDay = new Date()
    const date = new Date(data.dt * 1000)
    const time = getTimeString(date)

    return (<div style={hourStyles}>
        <div style={popStyles(data.pop)}></div>
        <h3 style={{margin: 0, fontWeight: 400}}>{time}</h3>
        <h2 style={{margin: 0, fontWeight: 800, fontSize: 32}}>{data.temp.toFixed(0)}°</h2>
        <img style={{width: '75%'}} src={getIconUrlFromCode(data.weather[0].id, data.night)} alt={data.weather[0].description} />
    </div>)
}

const BigTempSection = ({data}) => {
    const date = new Date(data.dt * 1000)
    const title = bigTempTextStyles
    const subtitle = bigTempSubtitleStyles

    return (<div style={bigTempSectionStyles}>
        <div style={bigTempItemStyles}>
            <img style={{width: '95%', position: 'absolute'}} src={getIconUrlFromCode(data.today.weather[0].id, false)} alt={data.today.weather[0].description} />
            <div style={{display: 'flex', flexDirection: 'row', position: 'absolute', bottom: 0}}>
                <div style={{display: 'flex', flexDirection: 'column', marginRight: 30}}>
                    <span style={{fontSize: 20, fontWeight: 400}}>High</span>
                    <span style={{fontSize: 48, fontWeight: 800, margin: 0, lineHeight: 1}}>{data.today.temp.max.toFixed(0)}°</span>
                </div>
                <div style={{display: 'flex', flexDirection: 'column'}}>
                    <span style={{fontSize: 20, fontWeight: 400}}>Low</span>
                    <span style={{fontSize: 48, fontWeight: 800, margin: 0, lineHeight: 1}}>{data.today.temp.min.toFixed(0)}°</span>
                </div>
            </div>
        </div>
    </div>)
}

/**
 * 
 * @param {Date} date 
 */
const getTimeString = (date) => {
    const time24 = (date.getHours() + 1)
    const ampm = time24 >= 12 && time24 !== 24 ? "PM" : "AM"
    let time12 = time24 % 12
    if (time12 === 0) time12 = 12
    return `${time12} ${ampm}`
}

function Divider({margin = 5}) {
    const styles = {
        display:'flex',
        width: '100%',
        height: '2px',
        background: '#fff2',
        marginBottom: margin,
        marginTop: margin,
    }
    return (<div style={styles}></div>)
}
