import Layout from '@/ui/layout/Layout'
import { AppProps } from 'next/app'
import './../src/ui/layout/common.css'
import Head from 'next/head'
import Script from 'next/script'
import { AuthContextProvider } from '@/store/auth-context'

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
        <title>Fake Plastic Trees</title>
      </Head>
      <Script
        src='https://cdn.counter.dev/script.js'
        data-id='ad8cfdc0-03e1-4bc9-9944-56c71e54a8c9'
        data-utcoffset='3'
      ></Script>
      <AuthContextProvider>
        <Layout>
          <Component {...pageProps} />
        </Layout>
      </AuthContextProvider>
    </>
  )
}

export default App
