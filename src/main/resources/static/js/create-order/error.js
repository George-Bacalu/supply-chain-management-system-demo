const container = document.querySelector("#container");
window.onmousemove = (event) => {
    container.style.backgroundPositionX = -event.clientX / 5 + "px";
    container.style.backgroundPositionY = -event.clientY / 5 + "px";
}