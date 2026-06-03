import { Layout } from "./weather-widget/layout.jsx";
import satori from "satori";
import { Resvg } from "@resvg/resvg-js";
import { readFileSync } from "fs";
import { getOpenWeatherMapData } from "../../api/openweathermap.js";

const getFont = (font) => {
  return readFileSync(`static/fonts/MonaSans/${font}.ttf`);
};

const fonts = {
  Light: getFont("Light"),
  Regular: getFont("Regular"),
  Bold: getFont("Bold"),
  Black: getFont("Black"),
};

export async function GET({ url }) {
  const data = await getOpenWeatherMapData();
  const title = url.searchParams.get("title") ?? "Hello World";

  const svg = await satori(Layout(data), {
    width: 1600,
    height: 1200,
    fonts: [
      { name: "MonaSans", data: fonts.Light, weight: 300 },
      { name: "MonaSans", data: fonts.Regular, weight: 400 },
      { name: "MonaSans", data: fonts.Bold, weight: 700 },
      { name: "MonaSans", data: fonts.Black, weight: 900 },
    ],
  });

  const resvg = new Resvg(svg);
  const png = resvg.render().asPng();

  return new Response(png, {
    headers: { "Content-Type": "image/png" },
  });
}
