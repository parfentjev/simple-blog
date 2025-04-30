import { FC, ReactNode, useEffect } from 'react'
import Header from './Header'
import Footer from './Footer'
import { useLocation } from 'react-router'
import { clearPageTitle } from '../../utils/title-utils'
import { Toaster } from 'react-hot-toast'

const Layout: FC<{ children: ReactNode }> = ({ children }) => {
  const location = useLocation()

  useEffect(() => {
    clearPageTitle()
  }, [location])

  return (
    <>
      <Toaster />
      <Header />
      <main>{children}</main>
      <Footer />
    </>
  )
}

export default Layout
