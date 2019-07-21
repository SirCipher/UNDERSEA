function decompose()
X = [0 0; 0 20; 10 50; 20 20; 20 0];

pg = polyshape(X(:,1),X(:,2));
tr = triangulation(pg);

plot(pg);
title('Input shape');

pdem = createpde;

pdem.geometryFromMesh(tr.Points', tr.ConnectivityList')
mesh = generateMesh(pdem, 'GeometricOrder', 'linear', 'Hmax', 2, 'Hgrad', 1.01);

% TODO: Preallocate array size
points = [];

for elem = mesh.Elements()
    % Convert to using the number of rows 
    pos_one = [mesh.Nodes(:,elem(1,:))'; mesh.Nodes(:,elem(2,:))'; mesh.Nodes(:,elem(3,:))'];

    x_cent = sum(pos_one(:,1)) / 3;
    y_cent = sum(pos_one(:,2)) / 3;
    
    points = [points; [x_cent, y_cent]];
end

% Plot overlayed pde and scatter
figure
hold on
pdeplot(pdem, 'ElementLabels','on');
scatter(points(:,1), points(:,2));
title('Centre points of each element');
hold off

% Plot pde
figure
pdeplot(pdem, 'ElementLabels','on');
title('Generated mesh');

% Centre points
figure
scatter(points(:,1), points(:,2));
title('Centre points of elements');
