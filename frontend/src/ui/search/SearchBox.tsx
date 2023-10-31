import { FC, FormEvent, useRef } from 'react'
import styles from './SearchBox.module.css'

const SEARCH_PROVIDER = 'https://duckduckgo.com/?q='

const SearchBox: FC<{}> = () => {
  const textInputRef = useRef<HTMLInputElement>(null)

  const handleOnSubmit = (event: FormEvent) => {
    event.preventDefault()

    if (!textInputRef.current) {
      return
    }

    const request =
      textInputRef.current.value + ' site:' + window.location.origin

    window.location.href = SEARCH_PROVIDER + encodeURIComponent(request)
  }

  return (
    <div className={styles.container}>
      <form onSubmit={handleOnSubmit}>
        <input type='text' ref={textInputRef} />
      </form>
    </div>
  )
}

export default SearchBox
