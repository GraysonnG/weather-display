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
    const index = Math.round(Math.random() * (catImages.length - 1))
    console.log(`${index}/${catImages.length}`)
    const imageToShow = catImages[index]

    return (<div style={{width: '100%', height: '100%', display: 'flex'}}>
        <img style={imgStyle} src={imageToShow} />
    </div>)
}