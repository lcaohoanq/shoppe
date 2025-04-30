import axios from 'axios'
import {config} from "../env/Constants.ts";

const instance = axios.create({
  baseURL: config.url.OMDB_BASE_URL
})
