import { Layout } from "./weather-widget/layout.jsx";
import satori from "satori";
import { Resvg } from "@resvg/resvg-js";
import { getOpenWeatherMapData } from "../../api/openweathermap.js";
import Light from "$lib/fonts/MonaSans/Light.ttf?inline";
import Regular from "$lib/fonts/MonaSans/Regular.ttf?inline";
import Bold from "$lib/fonts/MonaSans/Bold.ttf?inline";
import Black from "$lib/fonts/MonaSans/Black.ttf?inline";

export async function GET({ url }) {
  const data = await getOpenWeatherMapData();
  const title = url.searchParams.get("title") ?? "Hello World";

  function dataUrlToBuffer(dataUrl) {
    const base64 = dataUrl.split(",")[1];
    return Buffer.from(base64, "base64");
  }

  const svg = await satori(Layout(data), {
    width: 1600,
    height: 1200,
    fonts: [
      {
        name: "MonaSans",
        data: dataUrlToBuffer(Light),
        weight: 300,
      },
      {
        name: "MonaSans",
        data: dataUrlToBuffer(Regular),
        weight: 400,
      },
      {
        name: "MonaSans",
        data: dataUrlToBuffer(Bold),
        weight: 700,
      },
      {
        name: "MonaSans",
        data: dataUrlToBuffer(Black),
        weight: 900,
      },
    ],
  });

  const resvg = new Resvg(svg);
  const png = resvg.render().asPng();

  return new Response(png, {
    headers: { "Content-Type": "image/png" },
  });
}
