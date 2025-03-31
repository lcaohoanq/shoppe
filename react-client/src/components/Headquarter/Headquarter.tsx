import React from "react";
import {Link} from "@mui/material";
import useHeadquarters from "../../hooks/useHeadquarters";
import Loading from "../Loading";

const Headquarter : React.FC = () => {

  const { data, isLoading, error } = useHeadquarters()

  if (isLoading) return <Loading />
  if (error) return <div>Something went wrong</div>

  return (
    <div className='lg:col-span-2'>
      <div>
        Quốc gia & Khu vực:{' '}
        {data &&
          data.map((headQuarter) => (
            <Link key={headQuarter.id} href={headQuarter.domain_url} target='_blank' rel='noreferrer'>
              {headQuarter.region} |{' '}
            </Link>
          ))}
      </div>
    </div>
  )
}

export default Headquarter;