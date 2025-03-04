import { FC, ReactNode, useEffect } from 'react'
import Header from './Header'
import Notifications from './Notifications'
import Footer from './Footer'
import { useLocation } from 'react-router'
import { clearPageTitle } from '../../utils/title-utils'

const Layout: FC<{ children: ReactNode }> = ({ children }) => {
    const location = useLocation()

    useEffect(() => {
        clearPageTitle()
    }, [location])

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
