import { Profile } from "@/types";
import { useQuery } from "@tanstack/react-query";
import axios from "axios";

const fetchProfile = async (): Promise<Profile[]> => {
  const response = await axios.get(
    "https://6512cbd2b8c6ce52b3963937.mockapi.io/api/v1/profiles"
  );
  return response.data;
};

const useProfiles = () => {
  return useQuery({ queryKey: ["profile"], queryFn: fetchProfile });
};

export default useProfiles;
