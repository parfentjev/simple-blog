import { FC, FormEvent, useCallback, useRef } from 'react'
import { toast } from 'react-toastify'
import { useAuthContext } from '../../store/auth-context'
import { usersApi } from '../../api/api'

const AuthForm: FC = () => {
    const { signin } = useAuthContext()

    const usernameRef = useRef<HTMLInputElement>(null)
    const passwordRef = useRef<HTMLInputElement>(null)

    const submitHandler = useCallback(
        async (event: FormEvent) => {
            event.preventDefault()

            const username = usernameRef.current?.value
            const password = passwordRef.current?.value

            if (!username || !password) {
                toast.error("Username and password can't be empty.")

                return
            }

            usersApi()
                .usersTokenPost({ usersPostRequest: { username, password } })
                .then((token) => signin(token))
                .catch(() => toast.error("Couldn't auhtorize."))
        },
        [signin]
    )

    return (
        <div className="text-center">
            <h1>Authorize</h1>
            <form onSubmit={submitHandler}>
                <div>
                    <input
                        type="text"
                        placeholder="username"
                        ref={usernameRef}
                    />
                </div>
                <div>
                    <input
                        type="password"
                        placeholder="password"
                        ref={passwordRef}
                    />
                </div>
                <button type="submit" className="primary-button">
                    Submit
                </button>
            </form>
        </div>
    )
}

export default AuthForm
