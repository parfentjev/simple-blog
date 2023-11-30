import Container from '@/ui/layout/Container'
import LoginForm from '@/ui/admin/LoginForm'
import { FC } from 'react'
import { useRouter } from 'next/router'
import { useAuthContext } from '@/store/auth-context'

const LoginPage: FC = () => {
  const { push } = useRouter()
  const { token } = useAuthContext()

  if (token) {
    push('/admin')
  }

  return (
    <Container name='Log in' centered={true}>
      <LoginForm />
    </Container>
  )
}

export default LoginPage
