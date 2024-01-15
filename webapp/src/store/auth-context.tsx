import {
    FC,
    ReactNode,
    createContext,
    useCallback,
    useContext,
    useEffect,
    useState,
} from 'react'
import { TokenDto } from '../api/models/user'
import { redirect, useNavigate } from 'react-router-dom'

type AuthContextType = {
    token?: TokenDto
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
    const [token, setToken] = useState<TokenDto>()

    useEffect(() => {
        const localToken = loadLocalToken()

        if (!localToken) {
            return
        }

        const currentDate = new Date().getTime()
        const localTokenExpirationDate = localToken.expiration_date * 1000
        if (currentDate > localTokenExpirationDate) {
            removeLocalToken()
            setToken(undefined)
        }

        setToken(localToken)
    }, [])

    const handleSingIn = useCallback((token: TokenDto) => {
        setToken(token)
        saveLocalToken(token)
        navigate('/admin')
    }, [])

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

const saveLocalToken = (token: TokenDto) => {
    localStorage.setItem('token', JSON.stringify(token))
}

const loadLocalToken = (): TokenDto | null => {
    const token = localStorage.getItem('token')

    return token ? JSON.parse(token) : null
}

const removeLocalToken = () => {
    localStorage.removeItem('token')
}
