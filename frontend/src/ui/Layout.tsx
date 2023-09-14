import { FC } from 'react'
import { Link, NavLink, Outlet } from 'react-router-dom'

const Layout: FC = () => {
  return (
    <>
      <header>
        <nav>
          <ul>
            <li>
              <NavLink to='/'>posts</NavLink>
            </li>
            <li>
              <NavLink to='mailto:contact@fakeplastictrees.ee'>contact</NavLink>
            </li>
          </ul>
        </nav>
      </header>
      <main>
        {/* {navigation.state === 'loading' && <p>Loading...</p>} */}
        <Outlet />
      </main>
      <footer>
        <Link
          rel='license'
          to='http://creativecommons.org/licenses/by/4.0/'
          target='_blank'
        >
          license
        </Link>
        <Link
          to={process.env.REACT_APP_SERVICE_URL + `/swagger-ui/index.html#/`}
          target='_blank'
        >
          api
        </Link>
      </footer>
    </>
  )
}

export default Layout
