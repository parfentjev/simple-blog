import { FC, ReactNode } from 'react'
import Header from './Header'
import Footer from './Footer'
import { useAuthContext } from '@/store/auth-context'
import Container from './Container'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'

const Layout: FC<{ children: ReactNode }> = ({ children }) => {
  const { tokenExpirationDate } = useAuthContext()

  return (
    <>
      <ToastContainer
        position='top-center'
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme='dark'
      />
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
