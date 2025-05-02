import { FC, FormEvent, useRef } from 'react'

const SEARCH_PROVIDER = 'https://www.ecosia.org/search?q='

const SearchForm: FC = () => {
  const textInputRef = useRef<HTMLInputElement>(null)

  const handleOnSubmit = (event: FormEvent) => {
    event.preventDefault()

    const current = textInputRef.current
    if (!current) return

    const request = current.value + ' site:' + window.location.hostname.replace('www.', '')
    window.location.href = SEARCH_PROVIDER + encodeURIComponent(request)
  }

  return (
    <div className="text-center">
      <h1>Search</h1>
      <form onSubmit={handleOnSubmit}>
        <input type="text" placeholder="Search query..." autoFocus autoComplete="off" ref={textInputRef} />
      </form>
    </div>
  )
}

export default SearchForm
