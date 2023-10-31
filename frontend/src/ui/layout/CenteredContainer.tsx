import { ComponentProps, FC, ReactNode } from 'react'

const CenteredContainer: FC<{ children: ReactNode; name?: string }> = ({
  children,
  name,
}) => {
  return (
    <div className='text_centered'>
      {name && <h1>{name}</h1>}
      <div>{children}</div>
    </div>
  )
}

export default CenteredContainer
