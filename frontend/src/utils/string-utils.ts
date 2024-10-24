export const jsonDateToString = (date: Date) =>
    date.toLocaleDateString('uk')

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
