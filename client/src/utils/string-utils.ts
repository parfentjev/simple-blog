export const jsonDateToString = (date: string) =>
    new Date(date).toLocaleDateString('uk')

export const encodeTitle = (title: string) =>
    encodeURI(
        title
            .replaceAll(' ', '-')
            .replaceAll('.', '')
            .replaceAll('?', '')
            .replaceAll('!', '')
            .replaceAll('#', '')
            .replaceAll('@', '')
            .replaceAll(':', '')
            .replaceAll(',', '')
            .replaceAll('"', '')
	    .replaceAll('&', 'and')
            .toLocaleLowerCase()
    )
