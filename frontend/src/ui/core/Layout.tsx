import { FC, ReactNode, useEffect } from 'react'
import Header from './Header'
import Notifications from './Notifications'
import Footer from './Footer'
import { useLocation } from 'react-router-dom'
import { clearPageTitle } from '../../utils/title-utils'
import SeasonalImages from './seasonal/SeasonalImages'

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
            <SeasonalImages
                filename="/images/winter/winter-%index.webp"
                alt="Winter 2024/2025"
                totalFiles={7}
            />
            <Footer />
        </>
    )
}

export default Layout
