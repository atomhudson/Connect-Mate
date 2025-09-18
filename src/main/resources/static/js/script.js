console.log("Scri........!");

let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded", () => {
  changeTheme();
});

//TODO:
function changeTheme() {

  changePageTheme(currentTheme, "");
  const changeThemeButton = document.querySelector("#theme_change_button");

  changeThemeButton.addEventListener("click", (event) => {
    let oldTheme = currentTheme;
    if (currentTheme === "dark") {
      //theme to light
      currentTheme = "light";
    } else {
      //theme to dark
      currentTheme = "dark";
    }
    changePageTheme(currentTheme, oldTheme);
  });
}
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}
function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}
function changePageTheme(theme, oldTheme) {
  setTheme(currentTheme);
  if (oldTheme) {
    document.querySelector("html").classList.remove(oldTheme);
  }
  document.querySelector("html").classList.add(theme);
  document
    .querySelector("#theme_change_button")
    .querySelector("span").textContent = theme == "light" ? "Dark" : "Light";
}

