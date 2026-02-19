import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  
  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}
setItem(key: string, value: any): void {
  if (isPlatformBrowser(this.platformId)) {
    
    if (typeof value === 'string') {
      localStorage.setItem(key, value);
    } else {
      localStorage.setItem(key, JSON.stringify(value));
    }
  } else {
  }
}

  getItem(key: string): any {
    if (isPlatformBrowser(this.platformId)) {
      const item = localStorage.getItem(key);
      if (!item) return null;
      
      // Try to parse JSON, if fails return as string
      try {
        return JSON.parse(item);
      } catch {
        return item; // Return as string if not JSON
      }
    }
    return null;
  }

  removeItem(key: string): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem(key);
    }
  }

  clear(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.clear();
    }
  }
}