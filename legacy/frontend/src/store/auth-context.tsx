import TokenDto from '@/api/models/TokenDto'
import Redirecting from '@/ui/layout/Redirecting'
import { useRouter } from 'next/router'
import {
  FC,
  ReactNode,
  createContext,
  useCallback,
  useContext,
  useEffect,
  useState,
} from 'react'

const ONE_DAY_MILLISECONDS = 86400000

type AuthContextType = {
  token?: TokenDto
  tokenExpirationDate?: Date
  signin: Function
  signout: Function
}

const AuthContext = createContext<AuthContextType>({
  token: undefined,
  tokenExpirationDate: undefined,
  signin: () => {},
  signout: () => {},
})

export const AuthContextProvider: FC<{ children: ReactNode }> = ({
  children,
}) => {
  const { push } = useRouter()
  const [token, setToken] = useState<TokenDto>()
  const [tokenExpirationDate, setTokenExpirationDate] = useState<Date>()

  useEffect(() => {
    const localToken = loadLocalToken()

    if (!localToken) {
      return
    }

    const currentDate = new Date().getTime()
    const localTokenExpirationDate = localToken.expirationDate
    if (currentDate > localTokenExpirationDate) {
      console.log(currentDate, localTokenExpirationDate)
      removeLocalToken()
      setToken(undefined)
    } else if (currentDate > localTokenExpirationDate - ONE_DAY_MILLISECONDS) {
      setTokenExpirationDate(new Date(localTokenExpirationDate))
    }

    setToken(localToken)
  }, [])

  const handleSingIn = useCallback(
    (tokenDto: TokenDto) => {
      setToken(tokenDto)
      saveLocalToken(tokenDto)
      push('/admin')
    },
    [push],
  )

  const handleSignOut = useCallback(() => {
    removeLocalToken()
    setToken(undefined)
    setTokenExpirationDate(undefined)
  }, [])

  const value: AuthContextType = {
    token: token,
    tokenExpirationDate: tokenExpirationDate,
    signin: handleSingIn,
    signout: handleSignOut,
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export const useAuthContext = () => useContext(AuthContext)

export const ProtectedRoute: FC<{ children: ReactNode }> = ({ children }) => {
  const { token } = useAuthContext()
  const { push } = useRouter()

  useEffect(() => {
    if (!token) {
      push('/admin/login')
    }
  }, [push, token])

  return (token && <>{children}</>) || <Redirecting />
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
