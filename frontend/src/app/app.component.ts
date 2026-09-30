import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './app.component.html'
})
export class AppComponent {
    step = 1;
    width = 10;
    height = 10;
    cars: any[] = [];
    results: any[] | null = null;

    carName = '';
    startX: number | null = null;
    startY: number | null = null;
    direction = 'N';
    commands = '';
    error = '';

    constructor(private http: HttpClient) {}

    submitField() {
        if (this.width > 0 && this.height > 0) this.step = 2;
    }

    registerCar() {
        this.error = '';
        if (!this.carName.trim() || this.startX == null || this.startY == null || !this.commands.trim()) {
            this.error = 'All dynamic variables are required.';
            return;
        }
        if (this.startX < 0 || this.startX >= this.width || this.startY < 0 || this.startY >= this.height) {
            this.error = `Coordinates must slide within 0-${this.width - 1} x 0-${this.height - 1}.`;
            return;
        }
        if (this.cars.some(c => c.name.toLowerCase() === this.carName.trim().toLowerCase())) {
            this.error = 'Car identifier unique token collision!';
            return;
        }

        const clean = this.commands.toUpperCase().replace(/[^LRF]/g, '');
        if (clean.length !== this.commands.length) {
            this.error = 'Commands matrix strictly allows L, R, F values.';
            return;
        }

        this.cars.push({
            name: this.carName.trim(),
            initialX: this.startX,
            initialY: this.startY,
            initialOrientation: this.direction,
            commands: clean
        });

        this.carName = ''; this.startX = null; this.startY = null; this.commands = '';
    }

    executeLoop() {
        this.http.post<any[]>('/api/simulation/run', {
            width: this.width,
            height: this.height,
            cars: this.cars
        }).subscribe({
            next: (res) => this.results = res,
            error: () => this.error = 'Communication failure with core computing cluster.'
        });
    }

    wipe() {
        this.step = 1; this.cars = []; this.results = null; this.width = 10; this.height = 10; this.error = '';
    }

    exitApplication(): void {
           this.http.post('http://localhost:8080/api/system/shutdown', {}).subscribe({
            next: () => console.log('Shutdown sent'),
            error: () => console.log('Shutdown processing')
        });

         window.location.href = 'about:blank';
    }
}