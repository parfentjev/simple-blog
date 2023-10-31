import CenteredContainer from '@/ui/layout/CenteredContainer'
import LoginForm from '@/ui/admin/LoginForm'
import { FC } from 'react'
import { useRouter } from 'next/router'
import { useAuthContext } from '@/store/auth-context'

const LoginPage: FC<{}> = () => {
  const { push } = useRouter()
  const { token } = useAuthContext()

  if (token) {
    push('/admin')
  }

  return (
    <CenteredContainer name='Log in'>
      <LoginForm />
    </CenteredContainer>
  )
}

export default LoginPage
