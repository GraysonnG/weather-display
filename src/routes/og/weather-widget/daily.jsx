/** @jsxRuntime classic */
/** @jsx h */
import { loadImage } from '../../../helpers/imagehelper.js';
import { mmToInches } from '../../../helpers/imperial.js';
import { getIconUrlFromCode } from '../../../helpers/weatherIcon.jsx';
import { h, Fragment } from '../../../lib/jsx.js';

const days = [
    "Sun",
    "Mon",
    "Tue",
    "Wed",
    "Thu",
    "Fri",
    "Sat",
]

const dailySectionStyle = {
    display: 'flex',
    position: 'relative',
    height: 550,
    borderTop: '2px solid #0002',
    paddingTop: 15,
}

const dayItemStyle = {
    display: 'flex',
    flex: 1,
    flexDirection: 'column',
    borderRight: '2px solid #0002',
    paddingRight: 15,
    paddingLeft: 15,
    alignItems: 'center'
}

const lastItemStyle = {
    ...dayItemStyle,
    borderRight: '2px solid transparent'
}

const dayTempSectionStyle = {
    display: 'flex',
    flexDirection: 'column',
}

const dayInfoSectionStyle = {
    display: 'flex',
    flexDirection: 'row',
    width: '100%',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
}

const dayTempStyle = {
    margin: 0,
    fontSize: 32,
    fontWeight: 800,
    marginTop: -7
}

const infoDayStyle = {
    margin: 0,
    fontSize: 32,
    fontWeight: 600,
    marginBottom: -10,
}

const infoDateStyle = {
    margin: 0,
    fontSize: 24,
    fontWeight: 500,
    opacity: .5,
}

export const DailySection = ({data}) => {

    return (<div style={dailySectionStyle}>
        {data.map((dayData, i) => (<DayItem data={dayData} last={i===6}/>))}
    </div>)
}

const DayItem = ({data, last}) => {
    const dateObj = new Date(data.dt * 1000)
    const date = `${dateObj.getMonth() + 1}/${dateObj.getDate()}`
    const day = days[dateObj.getDay()]

    return (<div style={last ? lastItemStyle : dayItemStyle}>
        <div style={dayInfoSectionStyle}>
            <div style={{display: 'flex', marginTop: -7, flexDirection: 'column'}}>
                <h3 style={infoDayStyle}>{day}</h3>
                <h3 style={infoDateStyle}>{date}</h3>
            </div>
            <div style={dayTempSectionStyle}>
                <span style={dayTempStyle}>{data.temp.max.toFixed(0)}°</span>
                <span style={{
                    ...dayTempStyle,
                    opacity: .5,
                    marginTop: -10,
                    fontSize: 24,
                }}>{data.temp.min.toFixed(0)}°</span>
            </div>
        </div>
        <img style={{height: 150, marginTop: 30}} src={getIconUrlFromCode(data.weather[0].id, false)}/>
        <span style={{
            ...dayTempStyle,
            marginTop: -10,
            fontSize: 24,
            fontWeight: 600,
            paddingBottom: 30,
        }}>{(data.pop * 100).toFixed(0)}%</span>
        <MoreInfoSection data={data} />
    </div>)
}

const moreSectionRowStyles = {
    display: 'flex', 
    flexDirection: 'row', 
    width: '100%',
    marginBottom: 15,
}

const moreSectionColStyles = {
    display: 'flex',
    flexDirection: 'column',
    flex: 1,
    alignItems: 'center',
    paddingBottom: 15
}

const moreSectionItemTitleStyles = {
    fontSize: 16,
    fontWeight: 600,
}

const moreSectionItemValueStyles = {
    fontSize: 20,
    fontWeight: 800,
    textAlign: 'center',
    lineHeight: 1,
}

const MoreInfoSection = ({data}) => (<div style={{display: 'flex', flexDirection: 'column', width: '100%'}}>
    <div style={moreSectionRowStyles}>
        <div style={moreSectionColStyles}>
            <svg style={{width: 50, height: 50}} fill='#56a0ee' stroke='#fff' stroke-width="30" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 640 640"><path d="M160 384C107 384 64 341 64 288C64 245.5 91.6 209.4 129.9 196.8C128.6 190.1 128 183.1 128 176C128 114.1 178.1 64 240 64C283.1 64 320.5 88.3 339.2 124C353.9 106.9 375.7 96 400 96C444.2 96 480 131.8 480 176C480 181.5 479.4 186.8 478.4 192C478.9 192 479.5 192 480 192C533 192 576 235 576 288C576 341 533 384 480 384L160 384zM161.6 452.2C162.7 449.7 165.2 448 168 448C170.8 448 173.3 449.6 174.4 452.2L204.6 520.4C206.8 525.5 208 530.9 208 536.4C208 558.3 189.9 576 168 576C146.1 576 128 558.3 128 536.4C128 530.9 129.2 525.4 131.4 520.4L161.6 452.2zM313.6 452.2C314.7 449.7 317.2 448 320 448C322.8 448 325.3 449.6 326.4 452.2L356.6 520.4C358.8 525.5 360 530.9 360 536.4C360 558.3 341.9 576 320 576C298.1 576 280 558.3 280 536.4C280 530.9 281.2 525.4 283.4 520.4L313.6 452.2zM435.4 520.4L465.6 452.2C466.7 449.7 469.2 448 472 448C474.8 448 477.3 449.6 478.4 452.2L508.6 520.4C510.8 525.5 512 530.9 512 536.4C512 558.3 493.9 576 472 576C450.1 576 432 558.3 432 536.4C432 530.9 433.2 525.4 435.4 520.4z"/></svg>
            <span style={moreSectionItemTitleStyles}>Precip</span>
            <span style={moreSectionItemValueStyles}>{mmToInches((data.rain ?? 0)).toFixed(1)} in</span>
            {/* todo: make this a 1-5 scale */}
        </div>
        <div style={moreSectionColStyles}>
            <svg style={{width: 50, height: 50}} fill='#aaa' stroke='#fff' stroke-width="20" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 640 640"><path d="M352 96C352 113.7 366.3 128 384 128L424 128C437.3 128 448 138.7 448 152C448 165.3 437.3 176 424 176L96 176C78.3 176 64 190.3 64 208C64 225.7 78.3 240 96 240L424 240C472.6 240 512 200.6 512 152C512 103.4 472.6 64 424 64L384 64C366.3 64 352 78.3 352 96zM416 448C416 465.7 430.3 480 448 480L480 480C533 480 576 437 576 384C576 331 533 288 480 288L96 288C78.3 288 64 302.3 64 320C64 337.7 78.3 352 96 352L480 352C497.7 352 512 366.3 512 384C512 401.7 497.7 416 480 416L448 416C430.3 416 416 430.3 416 448zM192 576L232 576C280.6 576 320 536.6 320 488C320 439.4 280.6 400 232 400L96 400C78.3 400 64 414.3 64 432C64 449.7 78.3 464 96 464L232 464C245.3 464 256 474.7 256 488C256 501.3 245.3 512 232 512L192 512C174.3 512 160 526.3 160 544C160 561.7 174.3 576 192 576z"/></svg>
            <span style={moreSectionItemTitleStyles}>Wind</span>
            <span style={moreSectionItemValueStyles}>{data.wind_speed.toFixed(0)} mph</span>
            {/* todo: make this a 1-5 scale */}
        </div>
    </div>
    <div style={moreSectionRowStyles}>
        <div style={moreSectionColStyles}>
            <svg style={{width: 50, height: 50}} fill='#56a0ee' stroke='#fff' stroke-width="30" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 640 640"><path d="M320 576C214 576 128 490 128 384C128 292.8 258.2 109.9 294.6 60.5C300.5 52.5 309.8 48 319.8 48L320.2 48C330.2 48 339.5 52.5 345.4 60.5C381.8 109.9 512 292.8 512 384C512 490 426 576 320 576zM240 376C240 362.7 229.3 352 216 352C202.7 352 192 362.7 192 376C192 451.1 252.9 512 328 512C341.3 512 352 501.3 352 488C352 474.7 341.3 464 328 464C279.4 464 240 424.6 240 376z"/></svg>
            <span style={moreSectionItemTitleStyles}>Humidity</span>
            <span style={moreSectionItemValueStyles}>{data.humidity.toFixed(0)}%</span>
            {/* todo: make this a 1-5 scale */}
        </div>
        <div style={moreSectionColStyles}>
            <svg style={{width: 50, height: 50}} fill='#ffa500' stroke='#fff' stroke-width="15" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 640 640"><path d="M320 32C328.4 32 336.3 36.4 340.6 43.7L396.1 136.3L500.9 110C509.1 108 517.8 110.4 523.7 116.3C529.6 122.2 532 131 530 139.1L503.7 243.8L596.4 299.3C603.6 303.6 608.1 311.5 608.1 319.9C608.1 328.3 603.7 336.2 596.4 340.5L503.7 396.1L530 500.8C532 509 529.6 517.7 523.7 523.6C517.8 529.5 509 532 500.9 530L396.2 503.7L340.7 596.4C336.4 603.6 328.5 608.1 320.1 608.1C311.7 608.1 303.8 603.7 299.5 596.4L243.9 503.7L139.2 530C131 532 122.4 529.6 116.4 523.7C110.4 517.8 108 509 110 500.8L136.2 396.1L43.6 340.6C36.4 336.2 32 328.4 32 320C32 311.6 36.4 303.7 43.7 299.4L136.3 243.9L110 139.1C108 130.9 110.3 122.3 116.3 116.3C122.3 110.3 131 108 139.2 110L243.9 136.2L299.4 43.6L301.2 41C305.7 35.3 312.6 31.9 320 31.9zM320 176C240.5 176 176 240.5 176 320C176 399.5 240.5 464 320 464C399.5 464 464 399.5 464 320C464 240.5 399.5 176 320 176zM320 416C267 416 224 373 224 320C224 267 267 224 320 224C373 224 416 267 416 320C416 373 373 416 320 416z"/></svg>
            <span style={moreSectionItemTitleStyles}>UV index</span>
            <span style={moreSectionItemValueStyles}>{data.uvi}</span>
            {/*  todo: make this a 1-5 scale */}
        </div>

    </div>
    
</div>)
