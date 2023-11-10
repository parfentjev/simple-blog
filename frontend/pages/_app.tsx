import Layout from '@/ui/layout/Layout'
import { AppProps } from 'next/app'
import './../src/ui/layout/common.css'
import Head from 'next/head'
import { AuthContextProvider } from '@/store/auth-context'
import 'bootstrap-icons/font/bootstrap-icons.css'

const App = ({ Component, pageProps }: AppProps) => {
  return (
    <>
      <Head>
        <meta charSet='utf-8' />
        <link rel='icon' href='/favicon.ico' />
        <meta name='viewport' content='width=device-width, initial-scale=1' />
        <meta name='theme-color' content='#000000' />
        <meta
          name='description'
          content='A simple blog about technical topics, life and other things.'
        />
        <link rel='apple-touch-icon' href='/logo192.png' />
        <link rel='manifest' href='/manifest.json' />
        <link
          rel='alternate'
          type='application/rss+xml'
          title='Latest posts'
          href='/static/feed.xml'
        />
        <title>Fake Plastic Trees</title>
      </Head>
      <AuthContextProvider>
        <Layout>
          <Component {...pageProps} />
        </Layout>
      </AuthContextProvider>
    </>
  )
}

export default App
