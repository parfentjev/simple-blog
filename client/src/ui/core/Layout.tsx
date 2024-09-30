import { FC, ReactNode, useEffect } from 'react'
import Header from './Header'
import Notifications from './Notifications'
import Footer from './Footer'
import { useLocation } from 'react-router-dom'
import { clearPageTitle } from '../../utils/title-utils'
import SocialButtons from './SocialButtons'

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
            <SocialButtons />
            <Footer />
        </>
    )
}

export default Layout
