import { FC } from 'react'
import { Link } from 'react-router'

const Header: FC = () => {
  return (
    <header>
      <nav>
        <div>
          <Link to={'/'}>
            <img className="logo" src="/images/retro/logo.gif" alt="Logo" />
          </Link>
        </div>
        <ul>
          <li>
            <Link to={'/'}>
              <img src="/images/retro/menu-posts.gif" alt="Posts" />
            </Link>
          </li>
          <li>
            <Link to={'/search'}>
              <img src="/images/retro/menu-search.gif" alt="Search" />
            </Link>
          </li>
          <li>
            <a href="/feed.xml">
              <img src="/images/retro/menu-rss.gif" alt="Rss" />
            </a>
          </li>
        </ul>
      </nav>
    </header>
  )
}

export default Header
