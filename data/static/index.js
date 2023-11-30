const toggleMenu = () => {
    document.getElementById("menu").classList.toggle("navbar-visible")
}

const search = (event) => {
    event.preventDefault()

    const query = document.getElementById("search-query").value
    const request = query + " site:" + window.location.origin
    window.location.href = "https://duckduckgo.com/?q=" + encodeURIComponent(request)
}

document.getElementById("search-form")?.addEventListener("submit", search)
hljs.highlightAll();