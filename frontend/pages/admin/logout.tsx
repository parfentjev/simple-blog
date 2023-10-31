import { useAuthContext } from '@/store/auth-context'
import { useRouter } from 'next/router'
import { FC, useEffect } from 'react'

const LogoutPage: FC<{}> = () => {
  const { signout } = useAuthContext()
  const { push } = useRouter()

  useEffect(() => {
    signout()
    push('/')
  }, [push, signout])

  return null
}

export default LogoutPage
