import { FC, ReactNode } from 'react'
import Header from './Header'
import Footer from './Footer'
import { useAuthContext } from '@/store/auth-context'
import NamedContainer from './NamedContainer'

const Layout: FC<{ children: ReactNode }> = ({ children }) => {
  const { tokenExpirationDate } = useAuthContext()

  return (
    <>
      <Header />
      {tokenExpirationDate && (
        <NamedContainer centered={true}>
          <p>Your token expires on {tokenExpirationDate.toLocaleString()}</p>
        </NamedContainer>
      )}
      <main>{children}</main>
      <Footer />
    </>
  )
}

export default Layout
