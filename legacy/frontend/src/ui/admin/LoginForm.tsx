import { FC, FormEvent, useCallback, useRef } from 'react'
import styles from './LoginForm.module.css'
import Button from '../layout/element/Button'
import { postUsersToken } from '@/api/api-executor'
import { useAuthContext } from '@/store/auth-context'
import { toast } from 'react-toastify'

const LoginForm: FC<{}> = () => {
  const { signin } = useAuthContext()

  const usernameRef = useRef<HTMLInputElement>(null)
  const passwordRef = useRef<HTMLInputElement>(null)

  const handleSubmit = useCallback(
    async (event: FormEvent) => {
      event.preventDefault()

      const username = usernameRef.current?.value
      const password = passwordRef.current?.value

      if (!username || !password) {
        toast.error("Username and password can't be empty.")

        return
      }

      const result = await postUsersToken({
        username,
        password,
      })

      result.message ? toast.error(result.message) : signin(result)
    },
    [signin],
  )

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <div>
        <input type='text' placeholder='username' ref={usernameRef} required />
      </div>
      <div>
        <input
          type='password'
          placeholder='password'
          ref={passwordRef}
          required
        />
      </div>
      <div>
        <Button text='Submit' type='submit' />
      </div>
    </form>
  )
}

export default LoginForm
