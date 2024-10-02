import {
    FC,
    ReactNode,
    createContext,
    useCallback,
    useContext,
    useEffect,
    useState,
} from 'react'
import { useNavigate } from 'react-router-dom'
import { UserTokenDto } from '../api/codegen'

type AuthContextType = {
    token?: UserTokenDto
    signin: Function
    signout: Function
}

const AuthContext = createContext<AuthContextType>({
    token: undefined,
    signin: () => {},
    signout: () => {},
})

export const AuthContextProvider: FC<{ children: ReactNode }> = ({
    children,
}) => {
    const navigate = useNavigate()
    const [token, setToken] = useState<UserTokenDto>()

    useEffect(() => {
        const localToken = loadLocalToken()

        if (!localToken) {
            return
        }

        const currentDate = new Date().getTime()
        const localTokenExpirationDate = localToken.expires * 1000
        if (currentDate > localTokenExpirationDate) {
            removeLocalToken()
            setToken(undefined)
        }

        setToken(localToken)
    }, [])

    const handleSingIn = useCallback(
        (token: UserTokenDto) => {
            setToken(token)
            saveLocalToken(token)
            navigate('/admin')
        },
        [navigate]
    )

    const handleSignOut = useCallback(() => {
        removeLocalToken()
        setToken(undefined)
    }, [])

    const value: AuthContextType = {
        token: token,
        signin: handleSingIn,
        signout: handleSignOut,
    }

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export const useAuthContext = () => useContext(AuthContext)

export const ProtectedRoute: FC<{ children: ReactNode }> = ({ children }) => {
    const { token } = useAuthContext()

    return (
        (token && <>{children}</>) || (
            <p className="text-center">Not authorized.</p>
        )
    )
}

const saveLocalToken = (token: UserTokenDto) => {
    localStorage.setItem('token', JSON.stringify(token))
}

const loadLocalToken = (): UserTokenDto | null => {
    const token = localStorage.getItem('token')

    return token ? JSON.parse(token) : null
}

const removeLocalToken = () => {
    localStorage.removeItem('token')
}
