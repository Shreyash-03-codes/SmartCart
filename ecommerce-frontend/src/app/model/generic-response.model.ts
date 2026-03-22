export interface GenericResponse<T> {
  dateTime: string;
  data: T;
  error: string | null;
}