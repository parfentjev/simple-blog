import { FC, useState } from 'react'
import { Link } from 'react-router'

const Header: FC = () => {
    const [navbarVisible, setNavbarVisible] = useState(false)

    const toggleMenu = () => {
        setNavbarVisible(!navbarVisible)
    }

    return (
        <header>
            <nav>
                <button className="primary-button" onClick={toggleMenu}>
                    show menu
                </button>
                <ul className={navbarVisible ? 'navbar-visible' : ''}>
                    <li>
                        <Link to={'/'}>posts</Link>
                    </li>
                    <li>
                        <Link to={'/search'}>search</Link>
                    </li>
                    <li>
                        <a href="/feed.xml">rss</a>
                    </li>
                </ul>
            </nav>
        </header>
    )
}

export default Header
