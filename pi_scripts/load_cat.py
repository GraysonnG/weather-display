import requests
from inky.auto import auto
from PIL import Image
from io import BytesIO

display = auto()
print("Display Auto")
response = requests.get("https://creative-cheesecake-3607b7.netlify.app/cat")
print("Image request complete!")
img = Image.open(BytesIO(response.content))
print("Image request processed!")
display.set_image(img)
display.set_border(display.WHITE)
display.show()
