import Link from 'next/link'
import { FC, ReactNode } from 'react'
import Header from './Header'
import Footer from './Footer'

const Layout: FC<{ children: ReactNode }> = ({ children }) => {
  return (
    <>
      <Header />
      <main>{children}</main>
      <Footer />
    </>
  )
}

export default Layout
