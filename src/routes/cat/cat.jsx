/** @jsxRuntime classic */
/** @jsx h */
import { h, Fragment } from '../../lib/jsx.js';
import { getAllImages, loadImage } from '../../helpers/imagehelper.js';

const imgStyle = {
    width: '100%',
    height: '100%',
    objectFit: 'cover',
}

export const Cat = () => {
    const catImages = getAllImages("cats")

    const imageToShow = catImages[Math.round(Math.random() * (catImages.length - 1))]

    return (<div style={{width: '100%', height: '100%', display: 'flex'}}>
        <img style={imgStyle} src={imageToShow} />
    </div>)
}