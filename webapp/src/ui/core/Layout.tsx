import { FC, ReactNode } from 'react'
import Header from './Header'
import Notifications from './Notifications'
import Footer from './Footer'

const Layout: FC<{ children: ReactNode }> = ({ children }) => {
    return (
        <>
            <Notifications />
            <Header />
            <main>{children}</main>
            <Footer />
        </>
    )
}

export default Layout
