import NamedContainer from '@/ui/layout/NamedContainer'
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
    <NamedContainer name='Log in' centered={true}>
      <LoginForm />
    </NamedContainer>
  )
}

export default LoginPage
