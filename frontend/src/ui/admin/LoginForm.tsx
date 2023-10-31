import { FC, FormEvent, useCallback, useRef, useState } from 'react'
import styles from './LoginForm.module.css'
import Button from '../layout/element/Button'
import { postUsersToken } from '@/api/api-executor'
import { useAuthContext } from '@/store/auth-context'

const LoginForm: FC<{}> = () => {
  const { signin } = useAuthContext()

  const usernameRef = useRef<HTMLInputElement>(null)
  const passwordRef = useRef<HTMLInputElement>(null)

  const [errorMessage, setErrorMessage] = useState<string>()

  const handleSubmit = useCallback(async (event: FormEvent) => {
    event.preventDefault()

    if (errorMessage) {
      setErrorMessage(undefined)
    }

    const username = usernameRef.current?.value
    const password = passwordRef.current?.value

    if (!username || !password) {
      return
    }

    const result = await postUsersToken({
      username,
      password,
    })

    if (result.message) {
      setErrorMessage(result.message)
    } else {
      signin(result)
    }
  }, [])

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <div>
        <input type='text' placeholder='username' ref={usernameRef} />
      </div>
      <div>
        <input type='password' placeholder='password' ref={passwordRef} />
      </div>
      {errorMessage && <p>{errorMessage}</p>}
      <div>
        <Button text='Submit' type='submit' />
      </div>
    </form>
  )
}

export default LoginForm
