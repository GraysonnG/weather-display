import requests
from inky.auto import auto
from PIL import Image
from io import BytesIO

display = auto()
print("Display Auto")
response = requests.get("http://192.168.1.168:5173/og")
print("Image request complete!")
img = Image.open(BytesIO(response.content))
print("Image request processed!")
display.set_image(img)
display.set_border(display.WHITE)
display.show()
