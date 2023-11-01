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
  const { push } = useRouter()
  const [token, setToken] = useState<TokenDto>()

  useEffect(() => {
    const localToken = loadLocalToken()

    if (!localToken) {
      return
    }

    if (new Date().getTime() > localToken.expirationDate - 86400000) {
      removeLocalToken()
      setToken(undefined)
      // refresh token

      return
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
    setToken(undefined)
    removeLocalToken()
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
