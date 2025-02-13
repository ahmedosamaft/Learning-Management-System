import { clsx, type ClassValue } from 'clsx';
import { twMerge } from 'tailwind-merge';

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export function getToken() {
  return 'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiI3ZDJhZGFiYy1mNjY2LTQ0OTMtYjZlMi04MDVhMjAyZTRkNTEiLCJyb2xlIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNzM5NDgzNTQ2LCJleHAiOjE3Mzk0ODcxNDZ9.TUrps4H9WUwkTbIhZabG9YqTnMqMfnjX5uvurjAes1pC6ElvokALIqz2PO0dDLGm';
}

export function randomInt(min: number, max: number) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}