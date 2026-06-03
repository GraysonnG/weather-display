import { Resvg } from "@resvg/resvg-js"
import satori from "satori"

export const createImageResponse = async (
    rootJsxComponent,
    options,
    size = { width: 1600, height: 1200 },
) => {
    const svg = await satori(rootJsxComponent, size)
    const resvg = new Resvg(svg)
    const png = resvg.render().asPng()

    // todo: create some error handling logic and display a default error image.

    return new Response(png, {
        headers: { 'Content-Type': 'image/png' }
    })
}
