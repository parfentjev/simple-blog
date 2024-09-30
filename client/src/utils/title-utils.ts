const defaultTitle = 'Fake Plastic Trees'

export const setPageTitle = (title: string) => {
    document.title = `${title} — ${defaultTitle}`
}

export const clearPageTitle = () => {
    document.title = defaultTitle
}
