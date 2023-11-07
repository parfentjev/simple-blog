import { FC, ReactNode } from 'react'
import Header from './Header'
import Footer from './Footer'
import { useAuthContext } from '@/store/auth-context'
import Container from './Container'

const Layout: FC<{ children: ReactNode }> = ({ children }) => {
  const { tokenExpirationDate } = useAuthContext()

  return (
    <>
      <Header />
      {tokenExpirationDate && (
        <Container centered={true}>
          <p>Your token expires on {tokenExpirationDate.toLocaleString()}</p>
        </Container>
      )}
      <main>{children}</main>
      <Footer />
    </>
  )
}

export default Layout
