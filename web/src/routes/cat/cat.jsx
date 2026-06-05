/** @jsxRuntime classic */
/** @jsx h */
import { h, Fragment } from "../../lib/jsx.js";
import { getAllImages } from "../../helpers/imagehelper.js";

const imgStyle = {
  width: "100%",
  height: "100%",
  objectFit: "cover",
};

export const Cat = () => {
  const catImages = Object.values(
    import.meta.glob("$lib/cats/*.{png,jpg,jpeg}", {
      eager: true,
      query: "?inline",
    }),
  ).map((i) => i.default);
  const index = Math.round(Math.random() * (catImages.length - 1));
  console.log(`${index + 2}/${catImages.length}`);
  const imageToShow = catImages[index];

  return (
    <div style={{ width: "100%", height: "100%", display: "flex" }}>
      <img style={imgStyle} src={imageToShow} />
    </div>
  );
};
